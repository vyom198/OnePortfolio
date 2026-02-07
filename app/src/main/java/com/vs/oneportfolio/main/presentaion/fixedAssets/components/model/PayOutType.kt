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
}


fun Int.getPayOutType() : PayOutType {
    return when(this) {
        1 -> PayOutType.MONTHLY
        3 -> PayOutType.QUARTERLY
        12 -> PayOutType.ANNUAL
        else -> PayOutType.ANNUAL
    }


}
fun interestBasedOnPayoutType ( amt : Double , type : PayOutType ) : Double {
    return when(type){
        PayOutType.MONTHLY -> amt/12
        PayOutType.QUARTERLY -> amt/4
        PayOutType.ANNUAL -> amt
    }
}
fun PayOutType.getPayOutFrequency() : Int{
    return when(this) {
        PayOutType.MONTHLY -> 1
        PayOutType.QUARTERLY -> 3
        PayOutType.ANNUAL -> 12
    }
}


