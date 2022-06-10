from rest_framework import serializers

from .models import Comment
from posts.models import Post

from users.serializers import UserSerializer

class CommentSerializer(serializers.ModelSerializer):
    text = serializers.CharField() 
    post = serializers.PrimaryKeyRelatedField(queryset=Post.objects.all())
    author = serializers.HiddenField(default=serializers.CurrentUserDefault())

    class Meta:
        model = Comment
        fields =['text', 'post', 'author']
        read_only_fields = ['author']

class CommentDetailSerializer(serializers.ModelSerializer):
    text = serializers.CharField() 
    author = UserSerializer()

    class Meta:
        model = Comment
        fields =['id', 'text', 'author', 'created_on']
        read_only_fields = ['author']

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        rep['author'] = instance.author.username
        return rep
