import React, { Component } from "react";
import "./SongLength.css"

class SongLength extends React.Component {


    render() {
        return (
            <>
                <div className="song-length">   
                    <p className="p-song-length">{this.props.length}</p>
                </div>
            </>
        );
    }
}

export default SongLength;