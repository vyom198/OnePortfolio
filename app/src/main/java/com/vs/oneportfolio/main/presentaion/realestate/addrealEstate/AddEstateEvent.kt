package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate

interface AddEstateEvent {
    object  onAddEvent : AddEstateEvent
    object  LaunchPicker : AddEstateEvent
    object  Saved : AddEstateEvent

    object  onDelete : AddEstateEvent
    object OnSold : AddEstateEvent
}