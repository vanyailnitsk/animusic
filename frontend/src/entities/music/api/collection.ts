import {$host, collection} from "@/shared/api";
import {Playlist} from "@/entities/playlist";


export const getCollection = async ():Promise<Playlist> => {
    const response = await $host.get(collection)
    return response.data
}

export const removeFromCollection = (id: number) => {
    return $host.delete(collection, {
        params: {
            trackId: id
        }
    })
}

export const addToCollection = (id: number) => {
    return $host.post(collection, {}, {
        params: {
            trackId: id
        }
    })
}