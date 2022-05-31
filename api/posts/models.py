from django.db import models
from django.contrib.auth import get_user_model

from estates.models import Estate
from categories.models import Category

User = get_user_model()

class Post(models.Model):
    title = models.CharField(max_length=60)
    content = models.CharField(max_length=1000)
    estate = models.ForeignKey(Estate, on_delete=models.DO_NOTHING)
    category = models.ForeignKey(Category, on_delete=models.DO_NOTHING)
    author = models.ForeignKey(User, on_delete=models.DO_NOTHING)
    created_on = models.DateTimeField(auto_now_add=True)
    likes = models.ManyToManyField(User, blank=True, related_name='comment_likes')
    dislikes = models.ManyToManyField(User, blank=True, related_name='comment_dislikes')
    # image

    def __str__(self) -> str:
        return self.title + ' - ' + self.estate.name

    class Meta:
        ordering = ['created_on']