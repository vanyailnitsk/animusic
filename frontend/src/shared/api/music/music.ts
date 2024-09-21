import {$auth_host, playbackCounter} from "@/shared/api";
import {ErrorType} from "@/shared/types";


export const listeningTrackEvent = async (trackId:number) => {
    try{
        await $auth_host.post(playbackCounter,{
            trackId:trackId
        })
    }
    catch (e: unknown){
        const knownError = e as ErrorType
        console.log(knownError.message)
    }
}