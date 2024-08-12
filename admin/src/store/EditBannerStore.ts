import {makeAutoObservable} from "mobx";


class EditBannerStore {
    bannerImage:string | null
    color:string
    card:null | FormData
    constructor() {
        this.bannerImage = ''
        this.color = ''
        this.card = null
        makeAutoObservable(this)
    }
    setBannerImage(imageSrc:string){
        this.bannerImage = imageSrc
    }
    setColor(color:string){
        this.color = color
    }
    setCardImg(card: FormData | null){
        this.card = card
    }
}
export default EditBannerStore