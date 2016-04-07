#
# Copyright (c) Lucas Burson
# Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
#

BUILD := build

.PHONY: final
final: buildrpm

.PHONY: jar
jar:
	./gradlew --info release

.PHONY: buildrpm
buildrpm: jar
	mkdir -p build/rpm/opt/ljdelight/lights/jars
	mkdir -p build/rpm/etc/systemd/system/
	install --mode=0444 build/libs/LightsService.jar build/rpm/opt/ljdelight/lights/
	install --mode=0444 build/output/lib/*.jar build/rpm/opt/ljdelight/lights/jars
	install --mode=0444 LICENSE build/rpm/opt/ljdelight/lights/
	install --mode=0644 src/main/install/rpm/ljdelight-lights.service build/rpm/etc/systemd/system/
	fpm -s dir -t rpm -C build/rpm --force \
		--version 0.1.6 --name ljdelight-lights --architecture noarch \
		--before-install src/main/install/rpm/preinst.sh \
		--after-install src/main/install/rpm/postinst.sh \
		--before-remove src/main/install/rpm/prerm.sh \
		--maintainer "Lucas Burson" \
		--vendor "Lucas Burson" \
		--url http://ljdelight.com \
		--license BSL-1.0 \
		--rpm-user root --rpm-group root
	touch $@

# Use this target when the thrift file is modified.
#
.PHONY: gen-thrift
gen-thrift: src/main/thrift/lights.thrift
	thrift --gen java:hashcode,generated_annotations=suppress -out src/main/java/ $<

.PHONY: clean
clean:
	rm -rf ${BUILD}
	rm -f gen-thrift buildrpm *.rpm
