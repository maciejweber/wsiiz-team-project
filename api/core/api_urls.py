from django.urls import path,include
from rest_framework.authtoken import views

urlpatterns=[
    path('auth/', include('users.urls')),
    path('estates/', include('estates.urls'))
]