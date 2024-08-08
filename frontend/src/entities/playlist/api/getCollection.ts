import {Playlist} from "@/entities/playlist";
import {$host, collection} from "@/shared/api";

interface getCollectionResponse {
    data: Playlist
}

export const getCollection = async (): Promise<getCollectionResponse> => {
    const response = await $host.get(collection)
    return response
}