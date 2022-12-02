import React, { Component } from "react";
import "./AlbumData.css"

class AlbumData extends React.Component {

    render() {
        return (
            <>
                <div className="album-data">
                    <p align="center" className="p-album-title">{this.props.song.name}</p>
                    <p align="center" className="p-band-title">{this.props.song.band}</p>
                    <p align="center" className="p-songs-count">0</p>
                </div>
            </>
        );
    }
}

export default AlbumData;