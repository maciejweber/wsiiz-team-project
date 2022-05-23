from cProfile import label
from django.contrib.auth import authenticate
from django.utils.translation import gettext_lazy as _

from rest_framework import serializers

class LoginSerializer(serializers.Serializer):
    email = serializers.CharField(
        label=_("Email"),
        write_only=True
    )
    password = serializers.CharField(
        label=_("Password"),
        style={'input_type': 'password'},
        trim_whitespace=False,
        write_only=True
    )

    def validate(self, attrs):
        email = attrs.get('email')
        password = attrs.get('password')


        if email and password:
            user = authenticate(email=email, password=password)
            if not user:
                msg= _("Unable to log in with provided credentials.")
                raise serializers.ValidationError(msg, code='authorization')
        else:
            msg=_('Must include "email" and "passwod".')
            raise serializers.ValidationError(msg, code='authorization')

        attrs['user'] = user
        return attrs