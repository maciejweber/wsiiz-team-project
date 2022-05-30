from django.urls import path
from .views import EstateView

urlpatterns = [
    path('', EstateView.as_view())
]
