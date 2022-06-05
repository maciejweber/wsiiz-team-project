from django.db import models
from django.contrib.auth import get_user_model

from posts.models import Post

User = get_user_model()

class Comment(models.Model):
    text = models.TextField()
    created_on = models.DateTimeField(auto_now_add=True)
    post = models.ForeignKey(Post, on_delete=models.CASCADE, related_name='post')
    author = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self) -> str:
        return '{} - {}({})'.format(self.post.title, self.author.username, self.author.email) 