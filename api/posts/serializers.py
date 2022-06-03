from django.contrib.auth import get_user_model
from rest_framework import serializers
from rest_framework.fields import CurrentUserDefault


from .models import Post
from estates.models import Estate
from categories.models import Category

from estates.serializers import EstateSerializer
from users.serializers import UserSerializer
from categories.serializers import CategorySerializer

User = get_user_model()

class PostSerializer(serializers.ModelSerializer):
    title = serializers.CharField()
    estate = serializers.PrimaryKeyRelatedField(queryset=Estate.objects.all())
    category = serializers.PrimaryKeyRelatedField(queryset=Category.objects.all())
    author = serializers.HiddenField(default=serializers.CurrentUserDefault())
    likes_count = serializers.SerializerMethodField('get_likes_count')
    dislikes_count = serializers.SerializerMethodField('get_dislikes_count')

    def get_likes_count(self, obj):
        return obj.likes.count()
    def get_dislikes_count(self, obj):
        return obj.dislikes.count()

    class Meta:
        model = Post
        fields = ['id','title','estate','category','author','created_on','likes_count','dislikes_count']

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        rep['estate'] = instance.estate.name
        rep['category'] = EstateSerializer(instance.category).data
        rep['author'] = instance.author.username
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
        fields = ['id','title','content','estate','category','author','likes','dislikes']

class PostLikeSerializer(serializers.ModelSerializer):
    likes = UserSerializer(many=True, read_only=True)
    

    class Meta:
        model = Post
        fields = ['likes']
    
    def update(self, instance, validated_data):
        request_user = None
        request = self.context.get("request")
        if request and hasattr(request, "user"):
            request_user = request.user

        instance = super(PostLikeSerializer, self).update(instance, validated_data)

        if request_user in instance.likes.all():
            instance.likes.remove(request_user)
        else:
            instance.likes.add(request_user)

        return instance