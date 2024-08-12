import React, {ReactNode} from 'react';
import './DataCore.css'


const DataCore = ({page}: {page:ReactNode}) => {
    return (
        <div className='data__core__wrapper'>
            {page}
        </div>
    );
};

export default DataCore;