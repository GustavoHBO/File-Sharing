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
package file.share.server.model;

/**
 *
 * @author gustavo
 */
public class FileShared {
    
    private String way;
    private String name;
    private String extension;
    private String date;
    private String size;
    
    /**
     * Constructor Default.
     */
    public FileShared(){
        
    }
    
    /**
     * Constructor of the class.
     * @param way - Way of file.
     * @param name - Name of the file.
     * @param extension - Extension of the file.
     * @param date - Date of the file.
     * @param size -Size of the file.
     */
    public FileShared(String way, String name, String extension, String date, String size){
        this.way = way;
        this.name = name;
        this.extension = extension;
        this.date = date;
        this.size = size;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the way
     */
    public String getWay() {
        return way;
    }

    /**
     * @param way the way to set
     */
    public void setWay(String way) {
        this.way = way;
    }
}
