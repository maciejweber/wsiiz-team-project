from django.shortcuts import render
from rest_framework.permissions import IsAuthenticated
from rest_framework.views import APIView
from rest_framework.response import Response

from .serializers import EstateSerializer
from .models import Estate

class EstateView(APIView):
    serializer_class = EstateSerializer

    def get(self, request, format=None):
        estates = Estate.objects.all()
        serializer = EstateSerializer(estates, many=True)
        return Response(serializer.data)

