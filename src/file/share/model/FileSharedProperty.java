/*
 * Copyright (C) 2017 gustavo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package file.share.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gustavo
 */
public class FileSharedProperty {
    
    private StringProperty hash;
    private StringProperty size;
    private StringProperty way;
    private StringProperty name;
    private StringProperty extension;
    private StringProperty date;
    public String ipHost;
    
    /**
     * Constructor Default.
     * @param ipHost
     * @param hash - Identifier the file.
     * @param way - Path to file.
     * @param name - File name. 
     * @param extension - File extension.
     * @param date - Date of upload.
     * @param size - Size of archive.
     */
    public FileSharedProperty (String ipHost, String hash, String way, String name, String extension, String date, String size){
        this.ipHost = ipHost;
        this.hash = new SimpleStringProperty(hash);
        this.way = new SimpleStringProperty(way);
        this.name = new SimpleStringProperty(name);
        this.extension = new SimpleStringProperty(extension);
        this.date = new SimpleStringProperty(date);
        this.size = new SimpleStringProperty(size);
    }

    /**
     * @return the hash
     */
    public StringProperty getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(StringProperty hash) {
        this.hash = hash;
    }

    /**
     * @return the size
     */
    public StringProperty getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(StringProperty size) {
        this.size = size;
    }

    /**
     * @return the way
     */
    public StringProperty getWay() {
        return way;
    }

    /**
     * @param way the way to set
     */
    public void setWay(StringProperty way) {
        this.way = way;
    }

    /**
     * @return the name
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public StringProperty getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(StringProperty extension) {
        this.extension = extension;
    }

    /**
     * @return the date
     */
    public StringProperty getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(StringProperty date) {
        this.date = date;
    }

    /**
     * @return the ipHost
     */
    public String getIpHost() {
        return ipHost;
    }

    /**
     * @param ipHost the ipHost to set
     */
    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }
}
