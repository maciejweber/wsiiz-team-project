# Generated by Django 4.0.4 on 2022-06-03 00:40

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('posts', '0003_remove_post_dislikes_like_alter_post_likes'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='post',
            name='likes',
        ),
    ]
