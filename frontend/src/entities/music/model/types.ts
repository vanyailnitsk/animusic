import {ISoundtrack} from "@/entities/soundtrack";

export interface MusicState {
    listening_queue: ISoundtrack[];
    trackIndex: number;
    isPlaying: boolean;
    volume: number;
    fav_tracks: number[];
}
