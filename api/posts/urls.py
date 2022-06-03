from django.urls import path
from .views import PostDetailView, PostListView, PostLikeView

urlpatterns = [
    path('', PostListView.as_view()),
    path('<int:pk>/', PostDetailView.as_view()),
    path('<int:pk>/like', PostLikeView.as_view()),
]