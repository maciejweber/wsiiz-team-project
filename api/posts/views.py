from django.http import Http404

from rest_framework import status
from rest_framework.permissions import IsAuthenticated
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.generics import ListCreateAPIView
from django_filters import rest_framework as filters

from .serializers import PostSerializer, PostLikeSerializer,PostDetailSerializer
from .models import Post
from .permissions import IsPostOwnerOrAdmin

class EventFilter(filters.FilterSet):
    estate = filters.CharFilter(field_name="estate__name", method='filter_estate')
    category = filters.CharFilter(field_name="category__name", method='filter_category')

    class Meta:
        model = Post
        fields = ['estate']

    def filter_estate(self, queryset, name, estate):
        return queryset.filter(estate__name__contains=estate)

    def filter_category(self, queryset, name, category):
        return queryset.filter(category__name__contains=category)

class PostListView(ListCreateAPIView):
    serializer_class = PostSerializer
    filter_backends = [filters.DjangoFilterBackend]
    filter_class = EventFilter

    def get_queryset(self):
        return Post.objects.all()

    def perform_create(self, serializer):
        serializer.save(author=self.request.user)

class PostDetailView(APIView):
    permission_classes = [IsAuthenticated, IsPostOwnerOrAdmin]

    def get_object(self, pk):
        try:
            return Post.objects.get(pk=pk)
        except Post.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        post = self.get_object(pk)
        serializer = PostDetailSerializer(post)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        post = self.get_object(pk)
        self.check_object_permissions(request, post)
        serializer = PostDetailSerializer(post, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        post = self.get_object(pk)
        self.check_object_permissions(request, post)
        post.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

class PostLikeView(APIView):
    permission_classes = [IsAuthenticated]

    def get_object(self, pk):
        try:
            return Post.objects.get(pk=pk)
        except Post.DoesNotExist:
            raise Http404
    
    def put(self, request, pk, format=None):
        post = self.get_object(pk)
        context = {'request': request} 
        serializer = PostLikeSerializer(post, data=request.data, context=context)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
