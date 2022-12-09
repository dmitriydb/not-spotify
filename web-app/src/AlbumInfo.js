import React, { Component } from "react";
import "./AlbumInfo.css"
import AlbumCover from "./AlbumCover";
import AlbumData from "./AlbumData";
class AlbumInfo extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <div className="album-info">
                    <AlbumCover cover={this.props.song.albumCover}/>
                    <AlbumData song={this.props.song}/>
                </div>
            </>
        );
    }
}

export default AlbumInfo;