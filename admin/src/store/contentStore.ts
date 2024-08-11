// contentStore.js
import {makeAutoObservable} from 'mobx';

class ContentStore {
    currentContentType;
     constructor() {
         this.currentContentType = '';
         makeAutoObservable(this)
     }

    setCurrentContentType = (type: any) => {
        this.currentContentType = type;
    }
}

export default ContentStore