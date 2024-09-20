import {$auth_host, playbackCounter} from "@/shared/api";


export const listeningTrackEvent = async (trackId:number) => {
    try{
        await $auth_host.post(playbackCounter,{
            trackId:trackId
        })
    }
    catch (e){
        console.log(e.message)
    }
}