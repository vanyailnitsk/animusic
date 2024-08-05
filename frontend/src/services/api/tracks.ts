import {$host} from "./index";
import {IAnime} from "../../models/Anime";
import {Album} from "../../models/Albums";
import {Playlist} from "../../models/UserPlaylists";

export const collection = 'collection'

interface getAlbumResponse {
    data: Album
}

interface getFavoritesResponse {
    data: Playlist
}

export const getAlbumById = async (playlistId: string | undefined): Promise<getAlbumResponse> => {
    const response = await $host.get('albums/' + playlistId);
    return response;
}

export const getFavorites = async (): Promise<getFavoritesResponse> => {
    const response = await $host.get(collection)
    return response
}