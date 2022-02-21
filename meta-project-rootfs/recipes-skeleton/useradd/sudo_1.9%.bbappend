FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://add-user-to-sudo" 

do_install_append () {
        install ${S}/../add-user-to-sudo ${D}${sysconfdir}/sudoers.d/add-user-to-sudo
}
