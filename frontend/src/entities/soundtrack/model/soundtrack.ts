import {Image} from "@/shared/types";
import {IAlbum} from "@/entities/album";

export interface ISoundtrack {
    soundtrack: SoundtrackData
}

export interface SoundtrackData {
    id: number
    animeTitle: string
    audioFile: string
    duration: number
    image: Image | null
    originalTitle: string
    saved: boolean
    album: IAlbum
    anime: {
        id: number
        title: string
    }
}

export interface PlaylistSoundtrack {
    addedAt: string
    soundtrack: SoundtrackData
}