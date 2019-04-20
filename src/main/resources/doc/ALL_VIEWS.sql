CREATE OR REPLACE VIEW ALL_VIEWS
(owner, view_name, text_length, text, type_text_length, type_text, oid_text_length, oid_text, view_type_owner, 
view_type, superview_name, editioning_view, read_only)
AS
select u.name, o.name, v.textlength, v.text, t.typetextlength, t.typetext,
       t.oidtextlength, t.oidtext, t.typeowner, t.typename,
       decode(bitand(v.property, 134217728), 134217728,
              (select sv.name from superobj$ h, "_CURRENT_EDITION_OBJ" sv
              where h.subobj# = o.obj# and h.superobj# = sv.obj#), null),
       decode(bitand(v.property, 32), 32, 'Y', 'N'),
       decode(bitand(v.property, 16384), 16384, 'Y', 'N')
from sys."_CURRENT_EDITION_OBJ" o, sys.view$ v, sys.user$ u, sys.typed_view$ t
where o.obj# = v.obj#
  and o.obj# = t.obj#(+)
  and o.owner# = u.user#
  and (o.owner# = userenv('SCHEMAID')
       or o.obj# in
            (select oa.obj#
             from sys.objauth$ oa
             where oa.grantee# in ( select kzsrorol
                                         from x$kzsro
                                  )
            )
        or /* user has system privileges */
          exists (select null from v$enabledprivs
                  where priv_number in (-45 /* LOCK ANY TABLE */,
                                        -47 /* SELECT ANY TABLE */,
                                        -48 /* INSERT ANY TABLE */,
                                        -49 /* UPDATE ANY TABLE */,
                                        -50 /* DELETE ANY TABLE */)
                  )
      );
comment on column ALL_VIEWS.OWNER is 'Owner of the view';
comment on column ALL_VIEWS.VIEW_NAME is 'Name of the view';
comment on column ALL_VIEWS.TEXT_LENGTH is 'Length of the view text';
comment on column ALL_VIEWS.TEXT is 'View text';
comment on column ALL_VIEWS.TYPE_TEXT_LENGTH is 'Length of the type clause of the object view';
comment on column ALL_VIEWS.TYPE_TEXT is 'Type clause of the object view';
comment on column ALL_VIEWS.OID_TEXT_LENGTH is 'Length of the WITH OBJECT OID clause of the object view';
comment on column ALL_VIEWS.OID_TEXT is 'WITH OBJECT OID clause of the object view';
comment on column ALL_VIEWS.VIEW_TYPE_OWNER is 'Owner of the type of the view if the view is an object view';
comment on column ALL_VIEWS.VIEW_TYPE is 'Type of the view if the view is an object view';
comment on column ALL_VIEWS.SUPERVIEW_NAME is 'Name of the superview, if view is a subview';
comment on column ALL_VIEWS.EDITIONING_VIEW is 'An indicator of whether the view is an Editioning View';
comment on column ALL_VIEWS.READ_ONLY is 'An indicator of whether the view is a Read Only View';
