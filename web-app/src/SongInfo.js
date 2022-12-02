import React, { Component } from "react";
import "./SongInfo.css"

class SongInfo extends React.Component {


    render() {
        return (
            <>
                <div className="song-info">   
                    <p className="song-title">{this.props.title}</p>
                    <p className="band-title">{this.props.band}</p>
                </div>
            </>
        );
    }
}

export default SongInfo;