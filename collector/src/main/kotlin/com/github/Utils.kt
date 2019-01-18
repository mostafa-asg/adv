package com.github

import com.typesafe.config.Config

fun Config.toHashMap(): HashMap<String,Any> {
    val result = mutableMapOf<String,Any>()

    for (entry in this.entrySet()) {
        result.put(entry.key, entry.value.unwrapped())
    }

    return HashMap(result)
}