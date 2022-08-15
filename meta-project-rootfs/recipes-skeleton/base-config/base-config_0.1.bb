DESCRIPTION = "Basic configuration for our images"
LICENSE = "CECILL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/CECILL-2.0;md5=574109ac4bdff61f9c3e0de892ecbd19"

S = "${WORKDIR}"

SRC_URI += "file://hosts"
SRC_URI += "file://inputrc"
SRC_URI += "file://bashrc"
SRC_URI += "file://profile"
# In order to change hostname, add the following line in conf/local.conf:
# hostname_pn-base-files = "your_hostname_here"
#SRC_URI += "file://hostname"

do_install () {
  # create local conf directory
  install -d                                     ${D}${localstatedir}/local/config

  # set hosts default file
  #     didn't manage to dynamically copy original 'netbase' file
  install -d                                     ${D}${sysconfdir}/default
  install -m 0644 ${S}/hosts                     ${D}${sysconfdir}/default/


  install -d                                     ${D}${sysconfdir}/os-config/
  install -m 0755 ${S}/bashrc                    ${D}${sysconfdir}/os-config/bashrc
  install -m 0755 ${S}/profile                   ${D}${sysconfdir}/os-config/profile
  install -m 0600 ${S}/inputrc                   ${D}${sysconfdir}/os-config/inputrc
}

pkg_postinst_${PN} () {
  install -m 0755 $D${sysconfdir}/os-config/bashrc             $D${ROOT_HOME}/.bashrc
  install -m 0755 $D${sysconfdir}/os-config/profile            $D${ROOT_HOME}/.profile
  install -m 0755 $D${sysconfdir}/os-config/inputrc            $D${ROOT_HOME}/.inputrc

  install -m 0755 $D${sysconfdir}/os-config/bashrc             $D/home/user/.bashrc
  install -m 0755 $D${sysconfdir}/os-config/profile            $D/home/user/.profile
  install -m 0755 $D${sysconfdir}/os-config/inputrc            $D/home/user/.inputrc
}

DEPENDS += "useradd-user"
