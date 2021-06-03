package com.github.code.gambit.utility

object AppConstant {
    const val AUTH_ATTRIBUTE_CUSTOM_PROFILE = "custom:profile_image"
    const val BASE_URL = "https://mhv71te0rh.execute-api.ap-south-1.amazonaws.com/beta/"
    const val SHARE_URL = "https://vt-webclient.herokuapp.com/"
    const val FILTER_DATE_TEMPLATE = "yyyy-MM-dd"

    object Named {
        const val PERMISSION_ARRAY = "PERMISSION"
        const val USER_ID = "UID"
        const val BASE_URL = "BRL"
    }

    object API_PATH {
        const val USER_ID = "userId"
        const val FILE_ID = "fileId"
        const val URL_ID = "urlId"
    }

    object API_QUERY {
        const val FILE_LEK = "LastEvaluatedKey"
        const val FILE_SEARCH = "searchParam"
        const val FILTER_START = "start"
        const val FILTER_END = "end"
    }

    object Worker {
        const val FILE_URI_KEY = "FILE_URI"
        const val FILE_NAME_KEY = "FILE_NAME"
        const val FILE_SIZE_KEY = "FILE_SIZE"
        const val FILE_META_DATA = "FILE_META_DATA"
        const val USER_ID = "USER_ID"
        const val FILE_OUTPUT_KEY = "FILE_OUTPUT"
        const val FILE_UPLOAD_TAG = "FILE-UPLOAD-TAG"
    }

    object Notification {
        const val CHANNEL_ID = "com.github.code.gambit.utility.notification.channel.id"
        const val CHANNEL_NAME = "V Transfer Notification"
        const val CHANNEL_DESCRIPTION = "Default notification channel for all V Transfer notifications"
    }

    object Database {
        const val DB_NAME = "V-TRANSFER-MAIN-DATABASE"
    }
}
