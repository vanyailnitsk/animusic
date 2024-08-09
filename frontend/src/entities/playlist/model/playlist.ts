import {PlaylistSoundtrack} from "@/entities/soundtrack";

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
