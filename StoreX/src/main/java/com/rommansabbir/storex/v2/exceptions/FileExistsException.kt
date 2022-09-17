package com.rommansabbir.storex.v2.exceptions

import java.io.IOException

class FileExistsException(override val message: String) : IOException(message)