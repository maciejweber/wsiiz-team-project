from rest_framework import serializers
from .models import Estate

class EstateSerializer(serializers.Serializer):
    name = serializers.CharField(max_length=30)

    class Meta:
        model = Estate
        fields = '__all__'