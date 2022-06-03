from rest_framework import serializers

from .models import Comment
from posts.models import Post

from posts.serializers import PostSerializer
from users.serializers import UserSerializer

class CommentSerializer(serializers.ModelSerializer):
    text = serializers.CharField() 
    post = serializers.PrimaryKeyRelatedField(queryset=Post.objects.all())

    class Meta:
        model = Comment
        fields =['text', 'post', 'author']
        read_only_fields = ['author']

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        rep['author'] = UserSerializer(instance.author).data
        return rep

class CommentDetailSerializer(serializers.ModelSerializer):
    class Meta:
        model = Comment
        fields = '__all__'