from django.contrib.auth import get_user_model
from rest_framework import serializers
from rest_framework.fields import CurrentUserDefault

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
    likes = UserSerializer(read_only=True, many=True)
    dislikes = UserSerializer(read_only=True, many=True)

    class Meta:
        model = Post
        fields = '__all__'

    def to_representation(self, instance):
        rep = super().to_representation(instance)
        rep['estate'] = EstateSerializer(instance.estate).data
        rep['author'] = UserSerializer(instance.author).data
        return rep