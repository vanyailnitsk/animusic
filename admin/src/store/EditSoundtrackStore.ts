import {makeAutoObservable} from "mobx";


class EditSoundtrackStore{
    trackImg:string
    trackId:number | null
    constructor() {
        this.trackImg = ''
        this.trackId = null
        makeAutoObservable(this)
    }
    setTrackImage(imageSrc:string){
        this.trackImg = imageSrc
    }
    setTrackId(id:number){
        this.trackId = id
    }
}
export default EditSoundtrackStore