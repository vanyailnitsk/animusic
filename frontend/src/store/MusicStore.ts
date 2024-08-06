import {makeAutoObservable} from "mobx";
import {ISoundtrack, SoundtrackData} from "../models/Soundtracks";
import MusicService from "../services/MusicService";

class MusicStore{
    private _listening_queue: ISoundtrack[];
    private _trackIndex: number;
    private _isPlaying: boolean;
    volume: number;
    constructor(){
        this._listening_queue = JSON.parse(localStorage.getItem("listening_queue") || '[]')
        this._trackIndex = JSON.parse(localStorage.getItem("currentTrackIndex") || '0')
        this._isPlaying = false
        this.volume = Number(localStorage.getItem('volume')) || 0.5
        makeAutoObservable(this)
    }

    setPlaylist(listening_queue : ISoundtrack[]) {
        this._listening_queue = listening_queue
        localStorage.setItem("listening_queue", JSON.stringify(listening_queue));
    }
    changeVolume(volume : number) {
        this.volume = volume
        localStorage.setItem('volume', this.volume.toString())
    }
    addToFavorite(TrackId:number){
        MusicService.addToFavorite(TrackId)
    }
    setTrackIndex(index:number) {
        this._trackIndex = index

        localStorage.setItem("currentTrackIndex", JSON.stringify(index));
    }
    togglePlayPause() {
        this._isPlaying=!this._isPlaying
    }
    setIsPlaying(bool: boolean) {
        this._isPlaying=bool
    }
    nextTrack() {
        const nextIndex = this._trackIndex + 1;
        if (nextIndex < this._listening_queue.length) {
            this._trackIndex++;
        } else {
            this._trackIndex=0
        }
    }
    previousTrack() {
        const previousIndex = this._trackIndex - 1;
        if (previousIndex > 0) {
            this._trackIndex = previousIndex
        } else {
            this._trackIndex=0
        }
    }
    get playlist() {
        return this._listening_queue
    }

    get trackIndex():number {
        return this._trackIndex
    }
    get isPlaying():boolean {
        return this._isPlaying
    }
    trackEquals(track : SoundtrackData):boolean {
        return JSON.stringify(this.currentTrack)===JSON.stringify(track)
    }
    get currentTrack(): SoundtrackData | undefined  {
        if (this._listening_queue && this._listening_queue.length > this._trackIndex && this._trackIndex >= 0) {
            return this._listening_queue[this._trackIndex].soundtrack;
        } else {
            return undefined;
        }
    }
}

export default MusicStore;