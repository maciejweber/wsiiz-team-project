from django.contrib.auth import get_user_model
from rest_framework import serializers

from .models import Post
from estates.models import Estate

from estates.serializers import EstateSerializer
from users.serializers import UserSerializer

User = get_user_model()

class PostSerializer(serializers.ModelSerializer):
    title = serializers.CharField()
    content = serializers.CharField()
    estate = serializers.PrimaryKeyRelatedField(queryset=Estate.objects.all())
    author = serializers.HiddenField(default=serializers.CurrentUserDefault())
    likes_count = serializers.SerializerMethodField('get_likes_count')
    dislikes_count = serializers.SerializerMethodField('get_dislikes_count')

    def get_likes_count(self, obj):
        return obj.likes.count()
    def get_dislikes_count(self, obj):
        return obj.dislikes.count()

    class Meta:
        model = Post
        fields = ['id','title','content','estate','author','created_on','likes_count','dislikes_count']

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        rep['estate'] = EstateSerializer(instance.estate).data
        rep['author'] = UserSerializer(instance.author).data
        return rep


class PostDetailSerializer(serializers.ModelSerializer):
    title = serializers.CharField()
    content = serializers.CharField()
    estate = EstateSerializer()
    author = UserSerializer() 
    likes = UserSerializer(read_only=True, many=True)
    dislikes = UserSerializer(read_only=True, many=True)
    likes_count = serializers.SerializerMethodField('get_likes_count')
    dislikes_count = serializers.SerializerMethodField('get_dislikes_count')

    def get_likes_count(self, obj):
        return obj.likes.count()
    def get_dislikes_count(self, obj):
        return obj.dislikes.count()


    class Meta:
        model = Post
        fields = ['id','title','content','estate','author','likes','dislikes']