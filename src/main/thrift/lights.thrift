//
// Copyright (c) Lucas Burson
// Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
//

namespace java com.ljdelight.lights.generated

struct Location {
    1: required double lat;
    2: required double lng;
}

struct TaggedLocation {
    1: required i64 uid;
    2: required Location location;
}

struct Center {
    1: required Location location;
    16: optional i32 radiusInMeters = 65000;
}

service Lights {
    list<Location> getAllLocations(),
    list<TaggedLocation> getLocationsNear(1:Center center)
}
