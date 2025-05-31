package com.jetbrains.simplelogin

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()