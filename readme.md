Answer to Project 1:

If I have to update the likes count at a precise time, then I would use AlarmManager with PendingIntent as it performs tasks precisely on time. Also, it handles doze mode. But It gets cancelled if the phone reboots. To handle that I would use broadcast receiver to start the AlarmManager task.

Once the AlarmManager performs its task then I will maintain the updated likes count on RoomDb or SharedPreference.

By doing only this, the user can clear app data and cache, then the like count will be set to default. To avoid this I will also maintain the like count on some backend and fetch it on login to sync the like count.
