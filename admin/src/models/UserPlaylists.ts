import {SoundtrackData} from "./Soundtracks";

export interface Playlist{
    id: number,
    name: string,
    addedBy: {
        id: number,
        name: string,
        avatar: {
            url: string
        }
    }
    soundtracks:PlaylistSoundtrack[]
}
export interface PlaylistSoundtrack{
    addedAt:string
    soundtrack:SoundtrackData
}