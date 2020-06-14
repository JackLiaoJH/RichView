package com.lab68.richtextview.rich

/**
 *
 * @author jackson
 * @version 1.0
 * @date 2020/6/14 12:19 AM
 */
data class RichVo(
    var text: String = "",
    /** # @ [] ...*/
    var startRule: String = "",
    var endRule: String = ""
) {
}