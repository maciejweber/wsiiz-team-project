from cProfile import label
from django.contrib.auth import authenticate
from django.utils.translation import gettext_lazy as _
from django.contrib.auth import get_user_model

from rest_framework import serializers

User = get_user_model()

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


class RegisterSerializer(serializers.Serializer):
    email = serializers.CharField(
        label=_("Email"),
    )
    username = serializers.CharField(
        label=_("Name"),
    )
    password = serializers.CharField(
        label=_("Password"),
        style={'input_type': 'password'},
        trim_whitespace=False,
        write_only=True
    )
    password_confirm = serializers.CharField(
        label=_("Confirm password"),
        style={'input_type': 'password'},
        trim_whitespace=False,
        write_only=True
    )

    def validate(self, attrs):
        email = attrs.get('email')
        username = attrs.get('username')
        password = attrs.get('password')
        password_confirm = attrs.get('password_confirm')

        user_email = User.objects.filter(email=email).exists()
        if user_email:
            msg= _("User with this email already exists.")
            raise serializers.ValidationError(msg)

        user_username = User.objects.filter(username=username).exists()
        if user_username:
            msg= _("This username is already taken.")
            raise serializers.ValidationError(msg)

        if password != password_confirm:
            msg= _("Passwords are not the same.")
            raise serializers.ValidationError(msg)
        return attrs

    def create(self, validated_data):
        user = User.objects.create(
            email=validated_data['email'],
            username=validated_data['username'],
        )

        user.set_password(validated_data['password'])
        user.save()

        return user


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'username']