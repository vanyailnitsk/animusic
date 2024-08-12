import {makeAutoObservable, runInAction} from "mobx";
import {ISoundtrack, SoundtrackData} from "@/entities/soundtrack";
import {MusicService} from "@/shared/services";

export class MusicStore {
    private _listening_queue: ISoundtrack[];
    private _trackIndex: number;
    private _isPlaying: boolean;
    volume: number;
    fav_tracks: number[];

    constructor() {
        this._listening_queue = JSON.parse(localStorage.getItem("listening_queue") || '[]');
        this._trackIndex = JSON.parse(localStorage.getItem("currentTrackIndex") || '0');
        this._isPlaying = false;
        this.volume = Number(localStorage.getItem('volume')) || 0.5;
        this.fav_tracks = [];
        makeAutoObservable(this);
    }

    setPlaylist(listening_queue: ISoundtrack[]) {
        this._listening_queue = listening_queue;
        localStorage.setItem("listening_queue", JSON.stringify(listening_queue));
    }

    changeVolume(volume: number) {
        this.volume = volume;
        localStorage.setItem('volume', this.volume.toString());
    }

    async fetchFavTracks() {
        try {
            const tracks = await MusicService.getCollection();
            runInAction(() => {
                this.fav_tracks = tracks.soundtracks.map(track => track.soundtrack.id);
            });
        } catch (e) {
            console.log("Ошибка при загрузке треков:", e);
        }
    }

    async addToFavorite(trackId: number) {
        try {
            await MusicService.addToFavorite(trackId);
            runInAction(() => {
                this.fav_tracks = [...this.fav_tracks,trackId];
            });
        } catch (error) {
            console.error("Failed to add to favorites:", error);
        }
    }

    async removeFromFavorites(trackId: number) {
        try {
            await MusicService.removeFromFavorite(trackId);
            runInAction(() => {
                this.fav_tracks = this.fav_tracks.filter(id => id !== trackId);
            });
        } catch (error) {
            console.error("Failed to remove from favorites:", error);
        }
    }

    isSaved(id: number) {
        return this.fav_tracks.includes(id);
    }

    setTrackIndex(index: number) {
        this._trackIndex = index;
        localStorage.setItem("currentTrackIndex", JSON.stringify(index));
    }

    togglePlayPause() {
        this._isPlaying = !this._isPlaying;
    }

    setIsPlaying(bool: boolean) {
        this._isPlaying = bool;
    }

    nextTrack() {
        const nextIndex = this._trackIndex + 1;
        if (nextIndex < this._listening_queue.length) {
            this._trackIndex++;
        } else {
            this._trackIndex = 0;
        }
    }

    previousTrack() {
        const previousIndex = this._trackIndex - 1;
        if (previousIndex > 0) {
            this._trackIndex = previousIndex;
        } else {
            this._trackIndex = 0;
        }
    }

    get playlist() {
        return this._listening_queue;
    }

    get trackIndex(): number {
        return this._trackIndex;
    }

    get isPlaying(): boolean {
        return this._isPlaying;
    }

    trackEquals(track: SoundtrackData): boolean {
        return JSON.stringify(this.currentTrack) === JSON.stringify(track);
    }

    get currentTrack(): SoundtrackData | undefined {
        if (this._listening_queue && this._listening_queue.length > this._trackIndex && this._trackIndex >= 0) {
            return this._listening_queue[this._trackIndex].soundtrack;
        } else {
            return undefined;
        }
    }
}
