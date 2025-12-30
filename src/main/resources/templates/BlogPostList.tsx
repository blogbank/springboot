import React, { useState, useEffect } from 'react';
import { X, ExternalLink, User, FileText, Loader } from 'lucide-react';

interface BlogPost {
  sequenceNumber: number;
  authorName: string;
  title: string | null;
  link: string | null;
  memo: string | null;
}

interface BlogPostResponse {
  posts: BlogPost[];
  totalCount: number;
}

const BlogPostList: React.FC = () => {
  const [data, setData] = useState<BlogPostResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedPost, setSelectedPost] = useState<BlogPost | null>(null);
  const [isWebViewOpen, setIsWebViewOpen] = useState(false);

  const fetchBlogPosts = async () => {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch('/api/blogposts', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result: BlogPostResponse = await response.json();
      setData(result);
    } catch (err) {
      setError(err instanceof Error ? err.message : '데이터를 불러오는 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBlogPosts();
  }, []);

  const handlePostClick = (post: BlogPost) => {
    if (post.link && post.title) {
      setSelectedPost(post);
      setIsWebViewOpen(true);
    }
  };

  const closeWebView = () => {
    setIsWebViewOpen(false);
    setSelectedPost(null);
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <Loader className="w-8 h-8 animate-spin mx-auto mb-4 text-blue-600" />
          <p className="text-gray-600">블로그 게시글을 불러오는 중...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <X className="w-8 h-8 text-red-600" />
          </div>
          <h2 className="text-xl font-semibold text-gray-900 mb-2">오류가 발생했습니다</h2>
          <p className="text-gray-600 mb-4">{error}</p>
          <button
            onClick={fetchBlogPosts}
            className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
          >
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  if (!data) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <p className="text-gray-600">데이터가 없습니다.</p>
        </div>
      </div>
    );
  }

  const postsWithContent = data.posts.filter(post => post.title && post.link);
  const postsWithoutContent = data.posts.filter(post => !post.title || !post.link);

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="bg-white border-b border-gray-200 sticky top-0 z-10">
        <div className="max-w-4xl mx-auto px-4 py-6">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">블로그뱅크</h1>
              <p className="text-sm text-gray-500 mt-1">총 {data.totalCount}명의 개발자</p>
            </div>
            <div className="text-right">
              <div className="text-sm text-gray-500">게시글</div>
              <div className="text-lg font-semibold text-blue-600">{postsWithContent.length}개</div>
            </div>
          </div>
        </div>
      </div>

      <div className="max-w-4xl mx-auto px-4 py-8">
        {postsWithContent.length > 0 && (
          <div className="mb-12">
            <h2 className="text-lg font-semibold text-gray-800 mb-6 flex items-center">
              <FileText className="w-5 h-5 mr-2 text-blue-600" />
              최근 게시글
            </h2>
            <div className="grid gap-4">
              {postsWithContent.map((post) => (
                <div
                  key={post.sequenceNumber}
                  onClick={() => handlePostClick(post)}
                  className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 hover:shadow-lg hover:border-blue-200 transition-all duration-300 cursor-pointer group"
                >
                  <div className="flex items-start justify-between">
                    <div className="flex-1">
                      <div className="flex items-center mb-3">
                        <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white text-sm font-medium">
                          {post.authorName.charAt(0)}
                        </div>
                        <div className="ml-3">
                          <p className="font-medium text-gray-900">{post.authorName}</p>
                          <p className="text-sm text-gray-500">#{post.sequenceNumber}</p>
                        </div>
                      </div>

                      <h3 className="text-lg font-semibold text-gray-900 mb-2 group-hover:text-blue-600 transition-colors line-clamp-2">
                        {post.title}
                      </h3>

                      {post.memo && (
                        <p className="text-sm text-gray-600 mb-3">{post.memo}</p>
                      )}

                      <div className="flex items-center text-sm text-blue-600">
                        <ExternalLink className="w-4 h-4 mr-1" />
                        <span>글 읽기</span>
                      </div>
                    </div>

                    <div className="ml-4">
                      <div className="w-12 h-12 bg-gray-100 rounded-xl flex items-center justify-center group-hover:bg-blue-50 transition-colors">
                        <FileText className="w-6 h-6 text-gray-400 group-hover:text-blue-500" />
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {postsWithoutContent.length > 0 && (
          <div>
            <h2 className="text-lg font-semibold text-gray-800 mb-6 flex items-center">
              <User className="w-5 h-5 mr-2 text-gray-400" />
              준비중인 작성자들
            </h2>
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
              {postsWithoutContent.map((post) => (
                <div
                  key={post.sequenceNumber}
                  className="bg-white rounded-xl p-4 shadow-sm border border-gray-100"
                >
                  <div className="flex items-center">
                    <div className="w-8 h-8 bg-gray-200 rounded-full flex items-center justify-center text-gray-600 text-sm font-medium">
                      {post.authorName.charAt(0)}
                    </div>
                    <div className="ml-3 flex-1">
                      <p className="font-medium text-gray-700 text-sm">{post.authorName}</p>
                      <p className="text-xs text-gray-400">#{post.sequenceNumber}</p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>

      {isWebViewOpen && selectedPost && (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
          <div className="bg-white rounded-3xl w-full max-w-5xl h-[80vh] overflow-hidden shadow-2xl">
            <div className="bg-white border-b border-gray-200 px-6 py-4 flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center text-white text-sm font-medium">
                  {selectedPost.authorName.charAt(0)}
                </div>
                <div className="ml-3">
                  <h3 className="font-semibold text-gray-900">{selectedPost.authorName}</h3>
                  <p className="text-sm text-gray-500 truncate max-w-md">{selectedPost.title}</p>
                </div>
              </div>
              <button
                onClick={closeWebView}
                className="w-10 h-10 bg-gray-100 hover:bg-gray-200 rounded-full flex items-center justify-center transition-colors"
              >
                <X className="w-5 h-5 text-gray-600" />
              </button>
            </div>

            <div className="h-full pb-20">
              <iframe
                src={selectedPost.link || ''}
                className="w-full h-full border-0"
                title={selectedPost.title || ''}
                sandbox="allow-same-origin allow-scripts allow-forms allow-links"
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default BlogPostList;