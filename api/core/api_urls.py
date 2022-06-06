from django.urls import path,include
from rest_framework.authtoken import views

urlpatterns=[
    path('auth/', include('users.urls')),
    path('estates/', include('estates.urls')),
    path('posts/', include('posts.urls')),
    path('comments/', include('comments.urls')),
    path('categories/', include('categories.urls'))
]