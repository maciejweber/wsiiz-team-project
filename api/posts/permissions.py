from rest_framework.permissions import BasePermission


class IsPostOwnerOrAdmin(BasePermission):
    def has_object_permission(self, request, view, obj):
        if request.method == "DELETE": 
            return obj.author == request.user or request.user.is_staff

        return True