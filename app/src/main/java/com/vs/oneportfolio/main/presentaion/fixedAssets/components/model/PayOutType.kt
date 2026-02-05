package com.vs.oneportfolio.main.presentaion.fixedAssets.components.model

enum class PayOutType(
    val value: String
) {
    MONTHLY(
        value = "Monthly"
    ),
    QUARTERLY(
        value = "Quarterly"
    ),
    ANNUAL(
        value = "Annual"
    ),
    AT_MATURITY(
        value = "At Maturity"
    )
}


fun Int.getPayOutType() : PayOutType {
    return when(this) {
        1 -> PayOutType.MONTHLY
        3 -> PayOutType.QUARTERLY
        12 -> PayOutType.ANNUAL
        else -> PayOutType.AT_MATURITY

    }


}

fun PayOutType.getPayOutFrequency() : Int{
    return when(this) {
        PayOutType.MONTHLY -> 1
        PayOutType.QUARTERLY -> 3
        PayOutType.ANNUAL -> 12
        PayOutType.AT_MATURITY -> 0
    }
}


