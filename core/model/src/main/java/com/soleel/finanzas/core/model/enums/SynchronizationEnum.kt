package com.soleel.finanzas.core.model.enums

enum class SynchronizationEnum(
    val id: Int,
    val value: String
) {
    PENDING(id = 1, value = "Pendiente"), // Estado cuando se crea o actualiza un registro
    QUEUED(id = 2, value = "Encolado"),
    SYNCED(id = 3, value = "Sincronizado");

    companion object {
        fun fromId(id: Int): SynchronizationEnum {
            val synchronizationEnum: SynchronizationEnum? = SynchronizationEnum.entries.find(predicate = { it.id == id })
            return synchronizationEnum ?: PENDING
        }
    }
}


// TODO: Implementar modulo de sincronizacion
//{
//    "data": [
//    {
//        "entity": "ACCOUNT",
//        "data": "{...}" -> json a procesar
//    },
//    {
//        "entity": "TRANSACTION",
//        "data": "{...}" -> json a procesar
//    }, ...
//    ]
//}
//
//{
//    "status": "SUCCESS",
//    "data": [
//    {
//        "entity": "ACCOUNT",
//        "id": 1,
//    },
//    {
//        "entity": "TRANSACTION",
//        "id": 2,
//    }
//    ]
//}
//
//{
//    "status": "WARNING",
//    "data": [
//    {
//        "entity": "ACCOUNT",
//        "id": 1,
//    }
//    ],
//    "warnings": [
//    {
//        "entity": "TRANSACTION",
//        "id": 2,
//        "message": "Failed to update record"
//    },
//    {
//        "entity": "TRANSACTION",
//        "id": 3,
//        "message": "The account related with the transaction no exist"
//    }
//    ]
//}
//
//{
//    "status": "ERROR",
//    "errors": [
//    {
//        "code": "INVALID_REQUEST",
//        "message": "The request is invalid."
//    },
//    {
//        "code": "DATABASE_ERROR",
//        "message": "Failed to connect to the database."
//    }
//    ]
//}